import React, { useState, useEffect } from 'react';
import axios from 'axios';
import VacancyInWorkList from './VacancyInWorkList'; // Подключаем компонент списка вакансий

function CandidateCard({ candidate, onClose }) {
    const [candidateSkills, setCandidateSkills] = useState([]);
    const [showVacancyList, setShowVacancyList] = useState(false); // Состояние для отображения списка вакансий
    const [selectedVacancy, setSelectedVacancy] = useState(null); // Выбранная вакансия

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
        // Показываем список вакансий при нажатии на кнопку "Рассмотреть на вакансию"
        setShowVacancyList(true);
    };

    const handleVacancySelect = (vacancy) => {
        // Обработка выбора вакансии
        setSelectedVacancy(vacancy);
        // Дополните этот метод в соответствии с вашими требованиями
        console.log('Consider candidate for vacancy:', vacancy);
    };

    return (
        <div className="candidate-card-modal">
            <span className="close" onClick={onClose}>&times;</span>
            <h2>{candidate.firstName} {candidate.lastName}</h2>
            <p>Email: {candidate.email}</p>
            <h3>Навыки:</h3>
            <ul>
                {candidateSkills.map(skill => (
                    <li key={skill.skillsId}>{skill.name}</li>
                ))}
            </ul>
            <button onClick={handleConsider}>Рассмотреть на вакансию</button>

            {showVacancyList && (
                <VacancyInWorkList onSelect={handleVacancySelect} candidateId={candidate.candidateId} />
            )}
        </div>
    );
}

export default CandidateCard;
