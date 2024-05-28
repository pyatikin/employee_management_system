import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function RecruitmentReport({ setPageTitle }) {
    const [vacancies, setVacancies] = useState([]);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [reportData, setReportData] = useState(null);

    useEffect(() => {
        setPageTitle("Отчет по трудозатратам на подбор");
        fetchVacancies();
    }, [setPageTitle]);

    const fetchVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies/all');
            setVacancies(response.data);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        }
    };

    const fetchRecruitmentReport = async (vacancyId) => {
        try {
            const response = await axios.get(`http://localhost:8080/reports/recruitment/${vacancyId}`);
            setReportData(response.data);
        } catch (error) {
            console.error('Error fetching recruitment report:', error);
        }
    };

    const handleVacancyChange = (event) => {
        const vacancyId = event.target.value;
        setSelectedVacancy(vacancyId);
        fetchRecruitmentReport(vacancyId);
    };

    return (
        <div className="recruitment-report-container">
            <h2>Отчетность по трудозатратам</h2>
            <div className="vacancy-select">
                <label htmlFor="vacancy">Выберите вакансию:</label>
                <select id="vacancy" onChange={handleVacancyChange}>
                    <option value="">Выберите вакансию</option>
                    {vacancies.map(vacancy => (
                        <option key={vacancy.vacancyId} value={vacancy.vacancyId}>{vacancy.name}</option>
                    ))}
                </select>
            </div>
            {reportData && (
                <div className="report-details">
                    <h3>Отчет по вакансии "{reportData.name || 'Название вакансии не доступно'}"</h3>
                    <p>Время потраченное на подбор: {reportData.daysSpent==0?"В процессе":reportData.daysSpent}</p>
                    <p>Количество рассмотренных кандидатов: {reportData.candidateCount || '0 кандидата'}</p>
                    <p>Процент кандидатов прошедших интервью: {reportData.candidatesWithInterview + " %" || '0 %'}</p>
                    <p>Количество кандидатов прошедших телевонный разговор: {reportData.candidatesWithConversation + " %" || '0 %'}</p>
                    <p>Результат подбора: {reportData.recruitmentResult || 'В процессе'}</p>
                    <p>Выбранный кандидат: <Link to={`/candidates/${reportData.candidate?.candidateId || ''}`}>{reportData.candidate?.firstName + reportData.candidate?.lastName || 'Данные отсутствуют'}</Link></p>
                </div>
            )}
        </div>
    );
}

export default RecruitmentReport;