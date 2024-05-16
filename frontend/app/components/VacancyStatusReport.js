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
        // Пример расчета длины линии в зависимости от значения от 0 до 1
        return value * 100 + '%'; // Преобразуем значение в проценты для определения ширины линии
    };

    const calculateLineColor = (value) => {
        // Пример расчета цвета линии в зависимости от значения от 0 до 1
        if (value < 0.5) {
            return 'red';
        } else {
            return 'green';
        }
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
                {vacancyStatusCounts.map(status => (
                    <tr key={status.stage}>
                        <td>{status.stage}</td>
                        <td>{status.count}</td>
                        <td>
                            <div style={{ width: calculateLineWidth(status.relativeCount), height: '10px', backgroundColor: calculateLineColor(status.relativeCount) }}></div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default VacancyStatusReport;
