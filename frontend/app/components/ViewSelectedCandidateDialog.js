import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ViewSelectedCandidateDialog({ vacancy, onClose }) {
    const [candidate, setCandidate] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCandidate = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/vacancies/${vacancy.vacancyId}/selected-candidate`);
                setCandidate(response.data);
            } catch (err) {
                setError('Error fetching candidate data.');
                console.error('Error fetching candidate data:', err);
            } finally {
                setIsLoading(false);
            }
        };

        fetchCandidate();
    }, [vacancy.vacancyId]);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="candidate-card-modal">
            <span className="close" onClick={onClose}>&times;</span>
            <div>
                <h2>Информация об утвержденном кандидате</h2>
            </div>
            {candidate ? (
                <div >

                    <p><strong>Имя:</strong> {candidate.firstName + candidate.lastName}</p>
                    <p><strong>Email:</strong> {candidate.email}</p>
                    <p><strong>Телефон:</strong> {candidate.phone}</p>
                    <p><strong>Опыт работы:</strong> 2 года</p>
                    <p><strong>Навыки:</strong> Java</p>
                    <p><strong>Комментарии:</strong> Отличный кандидат, прекрасно разбирается в поставленной задаче</p>
                </div>
            ) : (
                <div>Кандидат не найден</div>
            )}
        </div>
    );
}

export default ViewSelectedCandidateDialog;
