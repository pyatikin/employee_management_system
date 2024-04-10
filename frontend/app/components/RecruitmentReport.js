import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function RecruitmentReport() {
    const [vacancies, setVacancies] = useState([]);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [reportData, setReportData] = useState(null);

    useEffect(() => {
        fetchVacancies();
    }, []);

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
            const response = await axios.get(`http://localhost:8080/reports/recrutment/${vacancyId}`);
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
        <div>
            <h2>Отчетность по трудозатратам</h2>
            <div>
                <label htmlFor="vacancy">Выберите вакансию:</label>
                <select id="vacancy" onChange={handleVacancyChange}>
                    <option value="">Выберите вакансию</option>
                    {vacancies.map(vacancy => (
                        <option key={vacancy.vacancyId} value={vacancy.vacancyId}>{vacancy.name}</option>
                    ))}
                </select>
            </div>
            {reportData && (
                <div>
                    <h3>Отчет по вакансии "{reportData.vacancy.name}"</h3>
                    <p>Время потраченное на подбор: {reportData.timeSpent}</p>
                    <p>Количество рассмотренных кандидатов: {reportData.candidatesReviewed}</p>
                    <p>Результат подбора: {reportData.recruitmentResult}</p>
                    <p>Выбранный кандидат: <Link to={`/candidates/${reportData.selectedCandidateId}`}>{reportData.selectedCandidateName}</Link></p>
                </div>
            )}
        </div>
    );
}

export default RecruitmentReport;
