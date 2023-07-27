import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';

function LoginComponent() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const navigate = useNavigate();
    const authContext = useAuth();

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = async () => {
        if (validateForm()) {
            if (await authContext.login(username, password)) {
                navigate(`/welcome/${username}`);
            } else {
                setShowErrorMessage(true);
            }
        }
    };

    const validateForm = () => {
        const errors = {};

        if (username.trim() === '') {
            errors.username = 'Username cannot be empty.';
        } else if (username.trim().length < 3 || username.trim().length > 20) {
            errors.username = 'Username should be between 3 and 20 characters.';
        }

        if (password.trim() === '') {
            errors.password = 'Password cannot be empty.';
        } else if (password.trim().length < 5) {
            errors.password = 'Password should be at least 5 characters long.';
        }

        setValidationErrors(errors);

        return Object.keys(errors).length === 0;
    };

    const [validationErrors, setValidationErrors] = useState({});

    return (
        <div className="row justify-content-center bg-light">
            <form className="mu-50 col-sm-6 mb-3 ml-3 bg-#f6f6f6">
                {showErrorMessage && (
                    <div className="errorMessage text-danger">Authentication Failed. Please check your credentials.</div>
                )}

                <div className={`form-outline mb-4 ${validationErrors.username ? 'has-validation' : ''}`}>
                    <label className="form-label" htmlFor="username">
                        Username
                    </label>
                    <input
                        type="text"
                        id="username"
                        className={`form-control ${validationErrors.username ? 'is-invalid' : ''}`}
                        name="username"
                        value={username}
                        onChange={handleUsernameChange}
                    />
                    {validationErrors.username && <div className="invalid-feedback">{validationErrors.username}</div>}
                </div>

                <div className={`form-outline mb-4 ${validationErrors.password ? 'has-validation' : ''}`}>
                    <label className="form-label" htmlFor="password">
                        Password
                    </label>
                    <input
                        type="password"
                        id="password"
                        className={`form-control ${validationErrors.password ? 'is-invalid' : ''}`}
                        name="password"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                    {validationErrors.password && <div className="invalid-feedback">{validationErrors.password}</div>}
                </div>

                <button type="button" className="btn btn-primary btn-block mb-4" name="login" onClick={handleSubmit}>
                    Sign in
                </button>

                <div className="text-center">
                    <p>
                        Not a member? <Link className=" " to="/register">Register</Link>
                    </p>
                </div>
            </form>
        </div>
    );
}

export default LoginComponent;
