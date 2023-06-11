import React, { useEffect, useState } from 'react'
import { useAuth } from '../security/AuthContext'
import { retrieveAllPublishedBooksApi } from '../api/EBookApiService'
import BookCard from './BookCard'
import { Link } from 'react-router-dom'

function Publications() {

  const {username} =useAuth()
  const [publications,setPublications]= useState([])


  useEffect( () =>{
    retriveAllPublications()
    // console.log(subscriptions)
},[])

  function retriveAllPublications() {

    retrieveAllPublishedBooksApi(username)
    .then((response) => setPublications(response.data))
    .catch((error) =>console.log(error))
  }
  return (
    <>
    <div> All Publications of the user, {username}</div>
    <div><Link className="btn btn-primary " to="/publish">Publish</Link></div>
    <div>
          {publications.map(
                (publication) => {

                  return (
                    <div>
                    <BookCard book = {publication.book}/>
                    <div>{publication.publicationDate}</div>
                    </div>
                  );
                }
              )}
        </div>
    </>
  )
}

export default Publications