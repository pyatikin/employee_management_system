import React, { useState, useEffect } from 'react';
import axios from 'axios';

function VacancyPage({ setPageTitle }) {
    const [vacancies, setVacancies] = useState([]);

    useEffect(() => {
        setPageTitle("Вакансии");
        fetchVacancies();
    }, [setPageTitle]);

    const fetchVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies'); // Ваш URL для запроса данных о вакансиях
            setVacancies(response.data);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        }
    };

    return (
        <div>
            <h2>Список вакансий</h2>
            <ul>
                {vacancies.map(vacancy => (
                    <li key={vacancy.id}>{vacancy.name}</li>
                ))}
            </ul>
        </div>
    );
}

export default VacancyPage;
