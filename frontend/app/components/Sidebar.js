import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';

function Sidebar() {
    const [vacancySubMenuOpen, setVacancySubMenuOpen] = useState(false);
    const [reportSubMenuOpen, setReportSubMenuOpen] = useState(false);

    const toggleSubMenu = () => {
        setVacancySubMenuOpen(!vacancySubMenuOpen);
    };

    const toggleReportSubMenu = () => {
        setReportSubMenuOpen(!reportSubMenuOpen);
    };

    const closeMenu = () => {
        document.querySelector('.sidebar').classList.remove('open');
    };

    return (
        <div className="sidebar">
            <nav>
                <div>
                    <div className="submenu">
                        <NavLink to="/employee-profile" activeClassName="active" onClick={closeMenu}>Профиль</NavLink> {/* Добавлен пункт меню для профиля */}
                        <span onClick={toggleSubMenu}>Вакансии</span>
                        {vacancySubMenuOpen && (
                            <ul>
                                <li><NavLink to="/open-vacancies" activeClassName="active" onClick={closeMenu}>Открытые</NavLink></li>
                                <li><NavLink to="/working-vacancies" activeClassName="active" onClick={closeMenu}>В работе</NavLink></li>
                                <li><NavLink to="/closed-vacancies" activeClassName="active" onClick={closeMenu}>Закрытые</NavLink></li>
                            </ul>
                        )}
                    </div>
                    <NavLink to="/candidates" activeClassName="active" onClick={closeMenu}>Соискатели</NavLink>
                    <NavLink to="/skills" activeClassName="active" onClick={closeMenu}>Навыки</NavLink>
                    <div className="submenu">
                        <span onClick={toggleReportSubMenu}>Отчеты</span>
                        {reportSubMenuOpen && (
                            <ul>
                                <li><NavLink to="/reports/recruitment" activeClassName="active" onClick={closeMenu}>Отчет по трудозатратам на подбор</NavLink></li>
                                <li><NavLink to="/reports/vacancy-status" activeClassName="active" onClick={closeMenu}>Отчет по статусам вакансий</NavLink></li>
                                <li><NavLink to="/reports/svod" activeClassName="active" onClick={closeMenu}>Сводный отчет</NavLink></li>
                            </ul>
                        )}
                    </div>

                </div>
            </nav>
        </div>
    );
}

export default Sidebar;
