import React from 'react';
import { NavLink } from 'react-router-dom';

function Sidebar() {
    const closeMenu = () => {
        document.querySelector('.sidebar').classList.remove('open');
    };

    return (
        <div className="sidebar">
            <nav>
                <NavLink to="/open-vacancies" activeClassName="active" onClick={closeMenu}>Открытые вакансии</NavLink>
                <NavLink to="/working-vacancies" activeClassName="active" onClick={closeMenu}>Вакансии в работе</NavLink>
                <NavLink to="/candidates" activeClassName="active" onClick={closeMenu}>Соискатели</NavLink>
                <NavLink to="/documents" activeClassName="active" onClick={closeMenu}>Документы</NavLink>
                <NavLink to="/reports" activeClassName="active" onClick={closeMenu}>Отчеты</NavLink>
            </nav>
        </div>
    );
}

export default Sidebar;
