import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

function VacancyInWorkList({ onSelect, candidateId }) {
    const [vacancies, setVacancies] = useState([]);

    useEffect(() => {
        fetchWorkingVacancies();
    }, []);

    const fetchWorkingVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies/working-vacancies');
            setVacancies(response.data);
        } catch (error) {
            console.error('Error fetching working vacancies:', error);
        }
    };

    const handleSelectVacancy = async (vacancyId) => {
        try {
            await axios.post(`http://localhost:8080/interviews/${vacancyId}/add-candidate`, { candidateId: candidateId });
            onSelect(vacancyId);
        } catch (error) {
            console.error('Error adding candidate to interview:', error);
        }
    };

    return (
        <div>
            <h2>Список вакансий в работе</h2>
            <ul>
                {vacancies.map(vacancy => (
                    <li key={vacancy.vacancyId}>
                        <div>
                            <h3>{vacancy.name}</h3>
                            <p>Описание: {vacancy.description}</p>
                            <p>Отдел: {vacancy.department.name}</p>
                            <button onClick={() => handleSelectVacancy(vacancy.vacancyId)}>Выбрать</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

VacancyInWorkList.propTypes = {
    onSelect: PropTypes.func.isRequired,
    candidateId: PropTypes.number.isRequired,
};

export default VacancyInWorkList;
