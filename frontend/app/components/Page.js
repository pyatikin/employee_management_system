import React from 'react';

function Page({ title, setPageTitle }) {
    React.useEffect(() => {
        setPageTitle(title);
    }, [title, setPageTitle]);

    return <h2>{title}</h2>;
}

export default Page;
