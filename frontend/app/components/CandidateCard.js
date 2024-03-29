import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CandidateCard({ candidate, onClose }) {
    const [candidateSkills, setCandidateSkills] = useState([]);

    useEffect(() => {
        fetchCandidateSkills();
    }, [candidate]);

    const fetchCandidateSkills = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/candidates/${candidate.candidateId}/skills`);
            setCandidateSkills(response.data);
        } catch (error) {
            console.error('Error fetching candidate skills:', error);
        }
    };

    const handleConsider = () => {
        // Дополните этот метод в соответствии с вашими требованиями
        console.log('Consider candidate for vacancy');
    };

    return (
        <div className="candidate-card-modal">
            <h2>{candidate.firstName} {candidate.lastName}</h2>
            <p>Email: {candidate.email}</p>
            <h3>Навыки:</h3>
            <ul>
                {candidateSkills.map(skill => (
                    <li key={skill.skillsId}>{skill.name}</li>
                ))}
            </ul>
            <button onClick={handleConsider}>Рассмотреть на вакансию</button>
            <button onClick={onClose}>Close</button>
        </div>
    );
}

export default CandidateCard;
