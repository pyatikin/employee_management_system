import React from 'react';
import { NavLink } from 'react-router-dom';

function Sidebar() {
    const closeMenu = () => {
        document.querySelector('.sidebar').classList.remove('open');
    };

    return (
        <div className="sidebar">
            <nav>
                <NavLink to="/vacancies" activeClassName="active" onClick={closeMenu}>Вакансии</NavLink>
                <NavLink to="/applicants" activeClassName="active" onClick={closeMenu}>Соискатели</NavLink>
                <NavLink to="/documents" activeClassName="active" onClick={closeMenu}>Документы</NavLink>
                <NavLink to="/reports" activeClassName="active" onClick={closeMenu}>Отчеты</NavLink>
            </nav>
        </div>
    );
}

export default Sidebar;
