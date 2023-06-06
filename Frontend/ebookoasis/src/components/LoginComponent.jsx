import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
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
        // <div className="Login vh-100 gradient-custom">
        //     <h1>Time to Login!</h1>
        //     {showErrorMessage && <div className="errorMessage">Authentication Failed.
        //         Please check your credentials.</div>}
        //     <div className="LoginForm">
        //         <form>
        //             <div>
        //                 <label className='form-label'>User Name:</label>
        //                 <input type="text" className='form-control' name="username" value={username} onChange={handleUsernameChange} />
        //             </div>
        //             <div>
        //                 <label className='form-label'>Password:</label>
        //                 <input type="password" className='form-control' name="password" value={password} onChange={handlePasswordChange} />
        //             </div>
        //             <div>
        //                 <button type="button" name="login" onClick={handleSubmit}>login</button>
        //             </div>
        //         </form>
        //     </div>
        // </div>

        <form className='mu-50'>
            {/* <!-- Email input --> */}
            {showErrorMessage && <div className="errorMessage">Authentication Failed.
        //         Please check your credentials.</div>}
            <div className="form-outline mb-4">
                <input type="email" id="form2Example1" className="form-control" name="username" value={username} onChange={handleUsernameChange} />
                <label className="form-label" for="form2Example1">Email address</label>
            </div>

            {/* <!-- Password input --> */}
            <div className="form-outline mb-4">
                <input type="password" id="form2Example2" className="form-control" name="password" value={password} onChange={handlePasswordChange} />
                <label className="form-label" for="form2Example2">Password</label>
            </div>



            {/* <!-- Submit button --> */}
            <button type="button" className="btn btn-primary btn-block mb-4" name="login" onClick={handleSubmit}>Sign in</button>

            {/* <!-- Register buttons --> */}
            <div className="text-center">
                <p>Not a member? <a href="#!">Register</a></p>

            </div>
        </form>
    )
}

export default LoginComponent