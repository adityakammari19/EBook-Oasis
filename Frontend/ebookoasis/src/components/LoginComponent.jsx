import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../security/AuthContext'

function LoginComponent() {

    const [username, setUsername] = useState('aditya')

    const [password, setPassword] = useState('')

    const [showErrorMessage, setShowErrorMessage] = useState(false)

    const navigate = useNavigate()

    const authContext = useAuth()

    function handleUsernameChange(event) {
        setUsername(event.target.value)
    }

    function handlePasswordChange(event) {
        setPassword(event.target.value)
    }

    async function handleSubmit() {
        if (await authContext.login(username, password)) {
            navigate(`/welcome/${username}`)
        } else {
            setShowErrorMessage(true)
        }
    }

    return (

        <div className='row justify-content-center'>
            <form className='mu-50 col-sm-6 mb-3 ml-3'>
                {/* <!-- Email input --> */}
                {showErrorMessage && <div className="errorMessage">Authentication Failed. Please check your credentials.</div>}
                <div className="form-outline mb-4">
                    <label className="form-label" htmlFor="username">Username</label>
                    <input type="text" id="username" className="form-control" name="username" value={username} onChange={handleUsernameChange} />
                </div>

                {/* <!-- Password input --> */}
                <div className="form-outline mb-4">
                    <label className="form-label" htmlFor="password">Password</label>
                    <input type="password" id="password" className="form-control" name="password" value={password} onChange={handlePasswordChange} />
                </div>

                {/* <!-- Submit button --> */}
                <button type="button" className="btn btn-primary btn-block mb-4" name="login" onClick={handleSubmit}>Sign in</button>

                {/* <!-- Register buttons --> */}
                <div className="text-center">
                    <p>Not a member? <Link className=" " to="/register">Register</Link></p>

                </div>
            </form>
        </div>
    )
}

export default LoginComponent