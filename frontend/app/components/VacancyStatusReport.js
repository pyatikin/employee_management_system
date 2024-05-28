import React, { useEffect, useState } from 'react';
import axios from 'axios';

function VacancyStatusReport({ setPageTitle }) {
    const [vacancyStatusCounts, setVacancyStatusCounts] = useState([]);

    useEffect(() => {
        setPageTitle("Отчет по статусам вакансий");
        fetchVacancyStatusCounts();
    }, [setPageTitle]);

    const fetchVacancyStatusCounts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/reports/vacancy-status');
            setVacancyStatusCounts(response.data);
        } catch (error) {
            console.error('Error fetching vacancy status counts:', error);
        }
    };

    const calculateLineWidth = (value) => {
        return value * 100 + '%'; // Преобразуем значение в проценты для определения ширины линии
    };

    const calculateLineColor = (index) => {
        // Используем индекс элемента для определения цвета
        const hue = (index / vacancyStatusCounts.length) * 120; // hue изменяется от 0 до 120
        return `hsl(${hue}, 100%, 50%)`;
    };

    return (
        <div>
            <h3>Отчет по статусам вакансий</h3>
            <table>
                <thead>
                <tr>
                    <th>Этап</th>
                    <th>Количество вакансий</th>
                    <th>Относительное количество</th> {/* Новый столбец для отображения линии */}
                </tr>
                </thead>
                <tbody>
                {vacancyStatusCounts.map((status, index) => (
                    <tr key={status.stage}>
                        <td>{status.stage}</td>
                        <td>{status.count}</td>
                        <td>
                            <div
                                style={{
                                    width: calculateLineWidth(status.relativeCount),
                                    height: '10px',
                                    backgroundColor: calculateLineColor(index) // Передаем индекс элемента
                                }}
                            ></div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default VacancyStatusReport;
