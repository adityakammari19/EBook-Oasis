import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../security/AuthContext'

function RegistrationComponent() {

  const [profile, setProfile] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email:"",
    password:"",
    phoneNumber:""
  })
 

  const [showErrorMessage, setShowErrorMessage] = useState(false)

  const navigate = useNavigate()

  const authContext = useAuth()


  const handleChange = e => {
    const { name, value } = e.target;
    setProfile(prevState => ({
        ...prevState,
        [name]: value
    }));
};
  async function handleSubmit() {
    if (await authContext.register(profile)) {
      // navigate(`/welcome/${profile.username}`)
      navigate(`/login`)
    } else {
      setShowErrorMessage(true)
    }
  }
  return (
    <div className='row justify-content-center'>
      <form className='mu-50 col-sm-6 mb-3 ml-3'>
        {/* <!-- Email input --> */}
        {showErrorMessage && <div className="errorMessage"> Please enter correct details.</div>}
        
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="firstName">Firstname</label>
          <input type="text" id="firstName" className="form-control" name="firstName" value={profile.firstName} onChange={handleChange}/>
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="lastName">Lastname</label>
          <input type="text" id="lastName" className="form-control" name="lastName" value={profile.lastName} onChange={handleChange}/>
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="email">Email</label>
          <input type="email" id="email" className="form-control" name="email" value={profile.email} onChange={handleChange} />
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="username">Username</label>
          <input type="text" id="username" className="form-control" name="username" value={profile.username} onChange={handleChange} />
        </div>

        

        {/* <!-- Password input --> */}
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="password">Password</label>
          <input type="password" id="password" className="form-control" name="password" value={profile.password}  onChange={handleChange} />
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="phoneNumber">Phone Number</label>
          <input type="text" id="phoneNumber" className="form-control" name="phoneNumber" value={profile.phoneNumber} onChange={handleChange} />
        </div>

        {/* <!-- Submit button --> */}
        <button type="button" className="btn btn-primary btn-block mb-4" name="register" onClick={handleSubmit}>Register</button>

        {/* <!-- Login buttons --> */}
        <div className="text-center">
          <p>Already a member? <Link className=" " to="/login">Login</Link></p>

        </div>
      </form>
    </div>
  )
}

export default RegistrationComponent