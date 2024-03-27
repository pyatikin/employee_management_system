import React from 'react';

function Home({ setPageTitle }) {
    React.useEffect(() => {
        setPageTitle("Главная");
    }, [setPageTitle]);

    return <h2>Главная</h2>;
}

export default Home;
