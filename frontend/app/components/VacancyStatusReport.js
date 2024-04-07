import React, { useEffect, useState } from 'react';
import axios from 'axios';

function VacancyStatusReport() {
    const [vacancyStatusCounts, setVacancyStatusCounts] = useState([]);

    useEffect(() => {
        console.log("VacancyStatusReport")
        fetchVacancyStatusCounts();
    }, []);

    const fetchVacancyStatusCounts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/reports/vacancy-status');
            setVacancyStatusCounts(response.data);
        } catch (error) {
            console.error('Error fetching vacancy status counts:', error);
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
                </tr>
                </thead>
                <tbody>
                {vacancyStatusCounts.map(status => (
                    <tr key={status.stage}>
                        <td>{status.stage}</td>
                        <td>{status.count}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default VacancyStatusReport;
