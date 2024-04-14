
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import ViewCandidatesInInterviewDialog from './ViewCandidatesInInterviewDialog';

function ViewCandidatesInInterview({ vacancy, onClose }) {
    const [candidates, setCandidates] = useState([]);
    const [selectedCandidate, setSelectedCandidate] = useState(null);
    const [interviewInfo, setInterviewInfo] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        fetchCandidatesInInterview();
    }, [vacancy]);

    const fetchCandidatesInInterview = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/interviews/${vacancy.vacancyId}/candidates`);
            setCandidates(response.data);
        } catch (error) {
            console.error('Error fetching candidates in interview:', error);
        }
    };

    const handleCandidateClick = async (candidate) => {
        setSelectedCandidate(candidate);
        try {
            const response = await axios.get(`http://localhost:8080/interviews/${vacancy.vacancyId}/candidates/${candidate.candidateId}`);
            setInterviewInfo(response.data);
            setIsModalOpen(true);
        } catch (error) {
            console.error('Error fetching interview info:', error);
        }
    };

    const handleCloseDialog = () => {
        setSelectedCandidate(null);
        setInterviewInfo(null);
        setIsModalOpen(false);
    };

    return (
        <div className="candidate-card-modal">
            <span className="close" onClick={onClose}>&times;</span>
            <h2>Кандидаты на собеседовании по вакансии "{vacancy.name}"</h2>
            <ul>
                {candidates.map(candidate => (
                    <li key={candidate.candidateId}>
                        <p>
                            {candidate.firstName} {candidate.lastName}
                            <button onClick={() => handleCandidateClick(candidate)}>
                                Просмотреть собеседование
                            </button>
                        </p>
                        <p>Email: {candidate.email}</p>
                    </li>
                ))}
            </ul>
            {isModalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={handleCloseDialog}>&times;</span>
                        <h3>Информация о собеседовании для {selectedCandidate.firstName} {selectedCandidate.lastName}</h3>
                        {interviewInfo ? (
                            <div>
                                <p>Номер собеседования: {interviewInfo.interviewId}</p>
                                {/* Добавьте другие детали о собеседовании */}
                            </div>
                        ) : (
                            <p>Loading...</p>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}

ViewCandidatesInInterview.propTypes = {
    vacancy: PropTypes.object.isRequired,
};

export default ViewCandidatesInInterview;
