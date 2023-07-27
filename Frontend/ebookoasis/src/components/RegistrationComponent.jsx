import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';

function RegistrationComponent() {
  const [profile, setProfile] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    phoneNumber: ''
  });

  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [validationErrors, setValidationErrors] = useState({});

  const navigate = useNavigate();
  const authContext = useAuth();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProfile((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const validateForm = () => {
    const errors = {};

    if (profile.firstName.trim() === '') {
      errors.firstName = 'First name cannot be empty.';
    }

    // lastName can be empty

    if (profile.username.trim() === '') {
      errors.username = 'Username cannot be empty.';
    } else if (profile.username.trim().length < 3 || profile.username.trim().length > 20) {
      errors.username = 'Username should be between 3 and 20 characters.';
    }

    if (profile.email.trim() === '') {
      errors.email = 'Email cannot be empty.';
    } else if (!/\S+@\S+\.\S+/.test(profile.email)) {
      errors.email = 'Invalid email address.';
    }

    if (profile.password.trim() === '') {
      errors.password = 'Password cannot be empty.';
    } else if (profile.password.trim().length < 5) {
      errors.password = 'Password should be at least 5 characters long.';
    }

    if (profile.phoneNumber.trim() === '') {
      errors.phoneNumber = 'Phone number cannot be empty.';
    } else if (!/^\d{10}$/.test(profile.phoneNumber.trim())) {
      errors.phoneNumber = 'Invalid phone number should contain only 10 digits.';
    }

    setValidationErrors(errors);

    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async () => {
    if (validateForm()) {
      if (await authContext.register(profile)) {
        navigate('/login');
      } else {
        setShowErrorMessage(true);
      }
    }
  };

  return (
    <div className="row justify-content-center bg-light">
      <form className="mu-50 col-sm-6 mb-3 ml-3">
        {showErrorMessage && <div className="errorMessage">Please enter correct details.</div>}

        <div className={`form-outline mb-4 ${validationErrors.firstName ? 'has-validation' : ''}`}>
          <label className="form-label" htmlFor="firstName">
            Firstname
          </label>
          <input
            type="text"
            id="firstName"
            className={`form-control ${validationErrors.firstName ? 'is-invalid' : ''}`}
            name="firstName"
            value={profile.firstName}
            onChange={handleChange}
          />
          {validationErrors.firstName && <div className="invalid-feedback">{validationErrors.firstName}</div>}
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="lastName">
            Lastname
          </label>
          <input
            type="text"
            id="lastName"
            className="form-control"
            name="lastName"
            value={profile.lastName}
            onChange={handleChange}
          />
        </div>

        <div className={`form-outline mb-4 ${validationErrors.email ? 'has-validation' : ''}`}>
          <label className="form-label" htmlFor="email">
            Email
          </label>
          <input
            type="email"
            id="email"
            className={`form-control ${validationErrors.email ? 'is-invalid' : ''}`}
            name="email"
            value={profile.email}
            onChange={handleChange}
          />
          {validationErrors.email && <div className="invalid-feedback">{validationErrors.email}</div>}
        </div>

        <div className={`form-outline mb-4 ${validationErrors.username ? 'has-validation' : ''}`}>
          <label className="form-label" htmlFor="username">
            Username
          </label>
          <input
            type="text"
            id="username"
            className={`form-control ${validationErrors.username ? 'is-invalid' : ''}`}
            name="username"
            value={profile.username}
            onChange={handleChange}
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
            value={profile.password}
            onChange={handleChange}
          />
          {validationErrors.password && <div className="invalid-feedback">{validationErrors.password}</div>}
        </div>

        <div className={`form-outline mb-4 ${validationErrors.phoneNumber ? 'has-validation' : ''}`}>
          <label className="form-label" htmlFor="phoneNumber">
            Phone Number
          </label>
          <input
            type="text"
            id="phoneNumber"
            className={`form-control ${validationErrors.phoneNumber ? 'is-invalid' : ''}`}
            name="phoneNumber"
            value={profile.phoneNumber}
            onChange={handleChange}
          />
          {validationErrors.phoneNumber && <div className="invalid-feedback">{validationErrors.phoneNumber}</div>}
        </div>

        <button type="button" className="btn btn-primary btn-block mb-4" name="register" onClick={handleSubmit}>
          Register
        </button>

        <div className="text-center">
          <p>
            Already a member? <Link className=" " to="/login">Login</Link>
          </p>
        </div>
      </form>
    </div>
  );
}

export default RegistrationComponent;
