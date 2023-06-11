import React from 'react'
import { useAuth } from '../security/AuthContext'
import { Link } from 'react-router-dom'

function Header() {
    const authContext = useAuth()
    const isAuthenticated = authContext.isAuthenticated
    const username = authContext.username

    function logout() {
        authContext.logout()
    }

    return (

        <header className="border-bottom border-light border-5 mb-5 p-2"  style={{backgroundColor:'cyan'}}>
            <div className="container">
                <div className="row">
                    <nav className="navbar navbar-expand-lg">
                        <a className="navbar-brand ms-2 fs-2 fw-bold text-black" href="/">E-Book Oasis</a>
                        <div className="collapse navbar-collapse">
                            <ul className="navbar-nav">
                                <li className="nav-item">
                                    {isAuthenticated
                                        && <Link className="nav-link" to={{pathname:`/welcome/${username}`}}>Home</Link>}

                                </li>
                                <li className="nav-item">
                                    {isAuthenticated
                                        && <Link className="nav-link" to="/subscriptions">Subscriptions</Link>}
                                </li>
                                <li className="nav-item">
                                    {isAuthenticated
                                        && <Link className="nav-link" to="/publications">Publications</Link>}
                                </li>
                            </ul>
                        </div>
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                {!isAuthenticated &&
                                    <Link className="nav-link" to="/login">Login</Link>}
                            </li>
                            <li className="nav-item">
                                {isAuthenticated &&
                                    <Link className="nav-link" to="/logout" onClick={logout}>Logout</Link>}
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </header>

    )
}

export default Header