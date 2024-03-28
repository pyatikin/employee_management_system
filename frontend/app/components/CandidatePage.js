import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CandidatePage({ setPageTitle }) {
    const [candidates, setCandidates] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        setPageTitle("Соискатели");
        fetchCandidates();
    }, [setPageTitle]);

    const fetchCandidates = async () => {
        try {
            const response = await axios.get('http://localhost:8080/candidates');
            setCandidates(response.data);
        } catch (error) {
            console.error('Error fetching candidates:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const filteredCandidates = candidates.filter(candidate => {
        const fullName = `${candidate.firstName} ${candidate.lastName}`.toLowerCase();
        return fullName.includes(searchTerm.toLowerCase());
    });

    return (
        <div>
            <h2>Соискатели</h2>
            <input
                type="text"
                placeholder="Поиск по имени"
                value={searchTerm}
                onChange={handleSearchChange}
            />
            <table>
                <thead>
                <tr>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {filteredCandidates.map(candidate => (
                    <tr key={candidate.candidateId}>
                        <td>{candidate.firstName}</td>
                        <td>{candidate.lastName}</td>
                        <td>{candidate.email}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default CandidatePage;
