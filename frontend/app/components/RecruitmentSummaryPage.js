import React, {useEffect, useState} from 'react';
import axios from 'axios';

function RecruitmentSummaryReport() {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [totalVacancies, setTotalVacancies] = useState(6);
    const [totalCandidates, setTotalCandidates] = useState(3);
    const [vacancyStatusStats, setVacancyStatusStats] = useState([{status: 'в работе', count: 3}, {
        status: 'открыта',
        count: 3
    }]);
    const [departmentStats, setDepartmentStats] = useState([{departmentName: 'ИТ', count: 3}, {
        departmentName: 'Офис',
        count: 2
    }, {departmentName: 'Отдел продаж', count: 1}]);
    const [positionStats, setPositionStats] = useState(
        [{positionName: 'Разработчик', count: 2},
            {positionName: 'Дизайнер', count: 1},
            {positionName: 'Менеджер по продажам', count: 1},
            {positionName: 'Аналитик', count: 1},
            {positionName: 'HR-специалист', count: 1}]);

    const fetchSummaryReport = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/reports/summary?startDate=${startDate}&endDate=${endDate}`);
            const data = response.data;
            setTotalVacancies(data.totalVacancies);
            setVacancyStatusStats(data.vacancyStatusStats);
            setDepartmentStats(data.departmentStats);
            setPositionStats(data.positionStats);
        } catch (error) {
            console.error('Error fetching summary report:', error);
        }
    };

    useEffect(() => {
        fetchSummaryReport();
    }, [startDate, endDate]);

    const handleDateChange = () => {
        // Обработка изменения даты
        fetchSummaryReport();
    };

    return (
        <div className="recruitment-summary-report">
            <h2>Сводный отчет о вакансиях</h2>
            <div className="date-picker">
                <label htmlFor="startDate">Дата начала:</label>
                <input type="date" id="startDate" value={startDate} onChange={(e) => setStartDate(e.target.value)}/>
                <label htmlFor="endDate">Дата окончания:</label>
                <input type="date" id="endDate" value={endDate} onChange={(e) => setEndDate(e.target.value)}/>
                <button onClick={handleDateChange}>Применить</button>
            </div>
            <div className="total-vacancies">
                <tbody>
                <tr>
                    <td>
                        <h3>Всего вакансий за период: {totalVacancies}</h3>
                    </td>
                    <td>
                        <h3>Всего кандидатов за период: {totalCandidates}</h3>
                    </td>
                </tr>
                </tbody>
            </div>
            <div className="vacancy-status-stats">
                <h3>Статистика по статусам вакансий</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Статус</th>
                        <th>Количество вакансий</th>
                    </tr>
                    </thead>
                    <tbody>
                    {vacancyStatusStats.map(stat => (
                        <tr key={stat.status}>
                            <td>{stat.status}</td>
                            <td>{stat.count}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className="department-stats">
                <h3>Количество вакансий по отделам</h3>
                <thead>
                <tr>
                    <th>Отдел</th>
                    <th>Количество вакансий</th>
                </tr>
                </thead>
                <tbody>
                {departmentStats.map(stat => (
                    <tr key={stat.departmentName}>
                        <td>{stat.departmentName}</td>
                        <td>{stat.count}</td>
                    </tr>
                ))}
                </tbody>
            </div>
            <div className="position-stats">
                <h3>Количество вакансий по должностям</h3>
                <thead>
                <tr>
                    <th>Должность</th>
                    <th>Количество вакансий</th>
                </tr>
                </thead>
                <tbody>
                {positionStats.map(stat => (
                    <tr key={stat.positionName}>
                        <td>{stat.positionName}</td>
                        <td>{stat.count}</td>
                    </tr>
                ))}
                </tbody>
            </div>
        </div>
    );
}

export default RecruitmentSummaryReport;
