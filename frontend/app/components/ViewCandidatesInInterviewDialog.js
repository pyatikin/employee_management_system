import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

function ViewCandidatesInInterviewDialog({ vacancy, onClose }) {
    const [candidates, setCandidates] = useState([]);

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

    return (
        <div className="candidate-card-modal">
            <h2>Кандидаты на собеседовании по вакансии "{vacancy.name}"</h2>
            <ul>
                {candidates.map(candidate => (
                    <li key={candidate.candidateId}>
                        <p>{candidate.firstName} {candidate.lastName}</p>
                        <p>Email: {candidate.email}</p>
                        {/* Другие детали о кандидате */}
                    </li>
                ))}
            </ul>
            <button onClick={onClose}>Close</button>
        </div>
    );
}

ViewCandidatesInInterviewDialog.propTypes = {
    vacancy: PropTypes.object.isRequired,
    onClose: PropTypes.func.isRequired,
};

export default ViewCandidatesInInterviewDialog;
