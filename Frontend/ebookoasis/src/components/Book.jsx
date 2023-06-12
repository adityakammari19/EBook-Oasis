import React, { useEffect, useState } from 'react'
import { retriveBookApi, subscribeToABookApi, unSubscribeToABookApi } from '../api/EBookApiService'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../security/AuthContext'

function Book() {
  const {bookId}  = useParams()
  const location  = useLocation()
  const navigate = useNavigate()
  const {username} = useAuth()

  const {fromSubscriptionRoute,fromHomeRoute,fromPublicationRoute} = location.state
  const [book,setBook] = useState({})

  useEffect( () => {
    retriveBookWithId(bookId)
  },[])

  function retriveBookWithId(bookId){
    retriveBookApi(bookId)
    .then((response) => setBook(response.data))
    .catch((error) =>console.log(error))
  }
  async function handleSubscribe(){
    try{
      const response = await subscribeToABookApi(username,bookId)

    if (response.status === 201) {
      console.log("subscribed to book with id :" + bookId)
      navigate(`/subscriptions`)
    } 
  }
  catch{
    console.log(location)
  }
  }
  async function handleUnSubscribe(){
    try{
      const response = await unSubscribeToABookApi(username,bookId)
      navigate(`/welcome/${username}`)
    }
    catch{
      console.log(location)
    }
  } 

  function handleRead(){
    
  }
  return (<>
    <div>{book.title} </div>
    <div>{fromSubscriptionRoute && <div><button className="btn btn-primary" type="button" onClick={handleRead}>Read</button><button className="btn btn-primary" type="button" onClick={handleUnSubscribe}>UnSubscribe</button></div>}</div>
    <div>{fromHomeRoute && <div><button className="btn btn-primary" type="button" onClick={handleSubscribe}>Subscribe</button></div>}</div>
    <div>{fromPublicationRoute && <div><button className="btn btn-primary" type="button">Read</button></div>}</div>
    </>
  )
}

export default Book