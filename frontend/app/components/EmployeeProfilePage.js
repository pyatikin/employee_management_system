import React, {useEffect, useState} from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function EmployeeProfilePage({setPageTitle}) {
    const [employee, setEmployee] = useState({
        firstName: 'Пяткин',
        lastName: 'Никита Денисович',
        position: 'Специалист по подбору'
    });
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        setPageTitle("Профиль сотрудника");
        fetchEmployeeProfile();
    }, [setPageTitle]);

    const fetchEmployeeProfile = async () => {
        try {
            // const response = await axios.get('http://localhost:8080/employee/profile');
            // setEmployee(response.data);
        } catch (error) {
            console.error('Error fetching employee profile:', error);
            setEmployee({
                firstName: 'Пяткин',
                lastName: 'Никита Денисович',
                position: 'Специалист по подбору'
            });
        }
    };

    const handlePasswordChange = async (e) => {
        e.preventDefault();
        setPasswordError('');
        setSuccessMessage('');

        if (newPassword !== confirmPassword) {
            setPasswordError('Новый пароль и подтверждение пароля не совпадают');
            return;
        }

        try {
            await axios.post('http://localhost:8080/employee/change-password', {
                oldPassword,
                newPassword
            });
            setSuccessMessage('Пароль успешно изменен');
            setOldPassword('');
            setNewPassword('');
            setConfirmPassword('');
        } catch (error) {
            setPasswordError('Ошибка при изменении пароля');
            console.error('Error changing password:', error);
        }
    };

    const handleLogout = () => {
        // Реализуйте логику выхода из системы
        console.log('Logout');
        navigate('/login');
    };

    return (
        <div className="employee-profile-page">
            <h2>Профиль сотрудника</h2>
            <div className="employee-info">
                <p><strong>ФИО:</strong> {employee.firstName} {employee.lastName}</p>
                <p><strong>Должность:</strong> {employee.position}</p>
            </div>
            <form onSubmit={handlePasswordChange} className="password-change-form">
                <h3>Смена пароля</h3>
                <div className="form-group">
                    <label>Старый пароль:</label>
                    <input type="password" value={oldPassword} onChange={(e) => setOldPassword(e.target.value)}
                           required/>
                </div>
                <div className="form-group">
                    <label>Новый пароль:</label>
                    <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)}
                           required/>
                </div>
                <div className="form-group">
                    <label>Подтвердите новый пароль:</label>
                    <input type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}
                           required/>
                </div>
                {passwordError && <p className="error">{passwordError}</p>}
                {successMessage && <p className="success">{successMessage}</p>}
                <div className="form-group">
                    <button type="submit">Сохранить</button>
                </div>
            </form>
            <button onClick={handleLogout} className="logout-button">Выйти</button>
        </div>
    );
}

export default EmployeeProfilePage;