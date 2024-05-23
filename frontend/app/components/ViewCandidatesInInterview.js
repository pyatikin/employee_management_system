import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

function ViewCandidatesInInterview({ vacancy, onClose }) {
    const [candidates, setCandidates] = useState([]);
    const [selectedCandidate, setSelectedCandidate] = useState(null);
    const [interviewInfo, setInterviewInfo] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [comment, setComment] = useState(null);
    const [editedInterviewEvaluation, setEditedInterviewEvaluation] = useState(null);
    const [editedConversationEvaluation, setEditedConversationEvaluation] = useState(null);
    const [editedRecommendationEvaluation, setEditedRecommendationEvaluation] = useState(null);
    const [skillsOptions, setSkillsOptions] = useState([]);

    useEffect(() => {
        fetchCandidatesInInterview();
    }, [vacancy]);

    useEffect(() => {
        fetchSkillsOptions();
    }, []);

    const fetchCandidatesInInterview = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/interviews/${vacancy.vacancyId}/candidates`);
            setCandidates(response.data);
        } catch (error) {
            console.error('Error fetching candidates in interview:', error);
        }
    };

    const fetchSkillsOptions = async () => {
        try {
            const response = await axios.get('http://localhost:8080/skills');
            const skillsData = response.data.map(skill => ({ value: skill.skillsId, label: skill.name }));
            setSkillsOptions(skillsData);
        } catch (error) {
            console.error('Error fetching skills:', error);
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

    const handleInterviewEvaluationChange = (event) => {
        setEditedInterviewEvaluation(event.target.value);
    };

    const handleConversationEvaluationChange = (event) => {
        setEditedConversationEvaluation(event.target.value);
    };

    const handleRecommendationEvaluationChange = (event) => {
        setEditedRecommendationEvaluation(event.target.value);
    };

    const handleCommentChange = (event) => {
        setComment(event.target.value);
    };

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleSaveEdit = async () => {
        try {
            const editedData = {
                interviewEvaluation: editedInterviewEvaluation,
                conversationEvaluation: editedConversationEvaluation,
                recommendationEvaluation: editedRecommendationEvaluation,
                comment: comment
            };
            await axios.put(`http://localhost:8080/interviews/${interviewInfo.interviewId}`, editedData);
            setInterviewInfo(editedData);
            setIsEditing(false);
        } catch (error) {
            console.error('Error saving edited interview:', error);
        }
    };

    const handleApproveCandidate = async (candidate) => {
        try {
            await axios.post(`http://localhost:8080/vacancies/${vacancy.vacancyId}/approve`, candidate.candidateId, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            onClose();
        } catch (error) {
            console.error('Error approving candidate:', error);
        }
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
                        <button className="approve-button" onClick={() => handleApproveCandidate(candidate)}>
                            Утвердить на вакансию
                        </button>
                    </li>
                ))}
            </ul>
            {isModalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={handleCloseDialog}>&times;</span>
                        <h3>Информация о собеседовании
                            для {selectedCandidate.firstName} {selectedCandidate.lastName}</h3>
                        {interviewInfo ? (
                            <div>
                                {isEditing ? (
                                    <div>
                                        <div className="form-group">
                                            <label>
                                                Оценка собеседования:
                                                <input
                                                    type="number"
                                                    value={editedInterviewEvaluation || ''}
                                                    onChange={handleInterviewEvaluationChange}
                                                />
                                            </label>
                                        </div>
                                        <div className="form-group">
                                            <label>
                                                Оценка разговора:
                                                <input
                                                    type="number"
                                                    value={editedConversationEvaluation || ''}
                                                    onChange={handleConversationEvaluationChange}
                                                />
                                            </label>
                                        </div>
                                        <div className="form-group">
                                            <label>
                                                Рекомендация:
                                                <input
                                                    type="number"
                                                    value={editedRecommendationEvaluation || ''}
                                                    onChange={handleRecommendationEvaluationChange}
                                                />
                                            </label>
                                        </div>
                                        <div className="form-group">
                                            <label>
                                                Комментарий:
                                                <textarea
                                                    value={comment || ''}
                                                    onChange={handleCommentChange}
                                                />
                                            </label>
                                        </div>
                                        <button onClick={handleSaveEdit}>Сохранить</button>
                                    </div>
                                ) : (
                                    <div>
                                        <div className="form-group">
                                            <p>Оценка собеседования: {interviewInfo.interviewEvaluation}</p>
                                        </div>
                                        <div className="form-group">
                                            <p>Оценка разговора: {interviewInfo.conversationEvaluation}</p>
                                        </div>
                                        <div className="form-group">
                                            <p>Рекомендация: {interviewInfo.recommendationEvaluation}</p>
                                        </div>
                                        <div className="form-group">
                                            <p>Комментарий: {interviewInfo.comment}</p>
                                        </div>
                                        <button onClick={handleEditClick}>Редактировать</button>
                                    </div>
                                )}
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
    onClose: PropTypes.func.isRequired,
};

export default ViewCandidatesInInterview;
