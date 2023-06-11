import React from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import AuthProvider, { useAuth } from '../security/AuthContext'
import Header from './Header'
import LoginComponent from './LoginComponent'
import LogoutComponent from './LogoutComponent'
import WelcomeComponent from './WelcomeComponent'
import ErrorComponent from './ErrorComponent'
import Subscriptions from './Subscriptions'
import Publications from './Publications'
import RegistrationComponent from './RegistrationComponent'
import PublishNewBook from './PublishNewBook'


function AuthenticatedRoute({ children }) {
    const authContext = useAuth()

    if (authContext.isAuthenticated)
        return children

    return <Navigate to="/" />
}

function EBookApp() {
    return (

        <div className='EBookApp'>
            <AuthProvider>
                <BrowserRouter>
                    <Header />
                    <Routes>
                        <Route path='/' element={<LoginComponent />} />
                        <Route path='/login' element={<LoginComponent />} />
                        <Route path='/register' element={<RegistrationComponent />} />
                        <Route path='/welcome/:username' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                            </AuthenticatedRoute>
                        } />
                        <Route path='/subscriptions' element={
                            <AuthenticatedRoute>
                                <Subscriptions />
                            </AuthenticatedRoute>
                        } />

                        <Route path='/publications' element={
                            <AuthenticatedRoute>
                                <Publications />
                            </AuthenticatedRoute>
                        } />

                        <Route path='/publish' element={
                            <AuthenticatedRoute>
                                <PublishNewBook />
                            </AuthenticatedRoute>
                        } />

                        <Route path='/logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent />
                            </AuthenticatedRoute>
                        } />

                        <Route path='*' element={<ErrorComponent />} />
                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}

export default EBookApp