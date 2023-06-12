import React, { useEffect, useState } from 'react'
import { useAuth } from '../security/AuthContext'
import { retrieveAllSubscribedBooksApi } from '../api/EBookApiService'
import BookCard from './BookCard'

function Subscriptions() {

  const {username} =useAuth()
  const [subscriptions,setSubscriptions]= useState([])
  const fromSubscriptionRoute = true


  useEffect( () =>{
    retriveAllSubscriptions()
    // console.log(subscriptions)
},[])

  function retriveAllSubscriptions() {

    retrieveAllSubscribedBooksApi(username)
    .then((response) => setSubscriptions(response.data))
    .catch((error) =>console.log(error))
  }


  return (
    <>
    <div> All Subscriptions of the user, {username}</div>
    <div>
          {subscriptions.map(
                (subscription) => {

                  return (
                    <div>
                    <BookCard book = {subscription.book} fromSubscriptionRoute = {fromSubscriptionRoute}/>
                    <div>{subscription.subscriptionDate}</div>
                    </div>
                  );
                }
              )}
        </div>
    </>
  )
}

export default Subscriptions