import React from 'react';
import { useAuth } from '../security/AuthContext';
import { Link } from 'react-router-dom';

function Header() {
    const authContext = useAuth();
    const isAuthenticated = authContext.isAuthenticated;
    const username = authContext.username;

    function logout() {
        authContext.logout();
    }

    return (
        <>
            <header className="border-bottom border-light border-5 mb-5 p-2  bg-danger  " style={{ width: '100%' }}>
                <nav className="navbar navbar-expand-lg navbar-light ">
                    <div className="container">
                        <a className="navbar-brand ms-2 fs-2 fw-bold text-black" href='/'>E-Book Oasis</a>
                        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul className="navbar-nav ml-auto " style={{ fontSize: '20px' }}>
                                <li className="nav-item">
                                    {isAuthenticated && <Link className="nav-link" to={{ pathname: `/welcome/${username}` }}>Home</Link>}
                                </li>
                                <li className="nav-item">
                                    {isAuthenticated && <Link className="nav-link" to="/subscriptions">Subscriptions</Link>}
                                </li>
                                <li className="nav-item">
                                    {isAuthenticated && <Link className="nav-link" to="/publications">Publications</Link>}
                                </li>
                            </ul>
                            <ul className="navbar-nav ms-auto " style={{ fontSize: '20px' }}>
                                <li className="nav-item">
                                    {!isAuthenticated && <Link className="nav-link" to="/login">Login</Link>}
                                </li>
                                <li className="nav-item">
                                    {isAuthenticated && <Link className="nav-link" to="/logout" onClick={logout}>Logout</Link>}
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>


        </>
    );
}

export default Header;


