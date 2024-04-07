import React, { useState } from 'react';
import {Navigate} from 'react-router-dom';


const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loggedIn, setLoggedIn] = useState(false); // Состояние для отслеживания успешной авторизации

    const handleLogin = (e) => {
        e.preventDefault(); // Отмена действия по умолчанию для предотвращения перезагрузки страницы

        // Ваша логика проверки логина и пароля здесь
        // Например, отправка данных на сервер для проверки

        // Предположим, что успешная авторизация происходит при наличии непустого логина и пароля
        if (username !== '' && password !== '') {
            setLoggedIn(true); // Устанавливаем состояние успешной авторизации
        }
    };

    if (loggedIn) {
        // Если пользователь успешно авторизован, перенаправляем на главную страницу
        return <Navigate to="/" />;
    }

    return (
        <div className="login-page">
            <h2>Авторизация</h2>
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <label htmlFor="username">Логин:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Пароль:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Войти</button>
            </form>
        </div>
    );
};

export default LoginPage;
