import React, { useState, useEffect } from 'react';
import axios from 'axios';
import VacancyInWorkList from './VacancyInWorkList'; // Подключаем компонент списка вакансий

function CandidateCard({ candidate, onClose }) {
    const [candidateSkills, setCandidateSkills] = useState([]);
    const [candidateResumes, setCandidateResumes] = useState([]); // Состояние для хранения резюме кандидата
    const [showVacancyList, setShowVacancyList] = useState(false); // Состояние для отображения списка вакансий
    const [selectedVacancy, setSelectedVacancy] = useState(null); // Выбранная вакансия

    useEffect(() => {
        fetchCandidateSkills();
        fetchCandidateResumes(); // Получаем резюме кандидата при загрузке компонента
    }, [candidate]);

    const fetchCandidateSkills = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/candidates/${candidate.candidateId}/skills`);
            setCandidateSkills(response.data);
        } catch (error) {
            console.error('Error fetching candidate skills:', error);
        }
    };

    const fetchCandidateResumes = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/candidates/${candidate.candidateId}/resumes`);
            setCandidateResumes(response.data);
        } catch (error) {
            console.error('Error fetching candidate resumes:', error);
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
            <h3>Резюме:</h3>
            <ul>
                {candidateResumes.map(resume => (
                    <li key={resume.resumeId}>{resume.content}</li>
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
