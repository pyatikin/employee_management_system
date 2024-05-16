/*
import React, { useEffect } from 'react';
import { Link, NavLink, useParams } from 'react-router-dom';
import VacancyStatusReport from "./VacancyStatusReport";
import RecruitmentReport from "./RecruitmentSummaryPage";

function Reports({ setPageTitle, setPageLinks }) {
    const { reportType } = useParams();

    useEffect(() => {
        setPageTitle("Отчеты");
        setPageLinks(
            <nav>
                <NavLink to="/reports/recruitment">Отчет по трудозатратам на подбор</NavLink>
                <NavLink to="/reports/vacancy-status">Отчет по статусам вакансий</NavLink>
                <NavLink to="/reports/svod">Сводный отчет</NavLink>
            </nav>
        );
    }, [setPageTitle, setPageLinks]);

    return (
        <div>
            <h2>{getReportTitle(reportType)}</h2>
            {renderReport(reportType)}
        </div>
    );
}

function getReportTitle(reportType) {
    switch (reportType) {
        case 'recruitment':
           return 'Отчет по трудозатратам на подбор';
        case 'vacancy-status': // Добавляем название для нового отчета
            return 'Отчет по статусам вакансий';
        case 'svod': // Добавляем название для нового отчета
            return 'Сводный отчет';
        default:
            return 'Неизвестный отчет';
    }
}

function renderReport(reportType) {
    switch (reportType) {
        case 'recruitment':
           return <RecruitmentReport />;
        case 'vacancy-status': // Добавляем компонент для нового отчета
            return <VacancyStatusReport />;
        case 'svod': // Добавляем компонент для нового отчета
            return <VacancyStatusReport />;
        default:
            return <p>Отчет не найден</p>;
    }
}

export default Reports;
*/
