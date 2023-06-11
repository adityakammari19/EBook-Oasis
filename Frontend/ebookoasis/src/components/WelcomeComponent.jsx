import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { retrieveAllBooksApi } from '../api/EBookApiService'
import BookCard from './BookCard'

function WelcomeComponent() {
  const { username } = useParams()



  const [books, setBooks] = useState([])


  useEffect(() => {

  }, [books])

  function handleRetrieveAllBooksApi() {
    retrieveAllBooksApi(username)
      .then((response) => {setBooks(response.data)})
      .catch((error) => console.log(error))
      .finally(() => console.log('cleanup'))

  }


  return (
    <div className="WelcomeComponent">
      <h1>Welcome {username}</h1>

      <div>
        <button className="btn btn-success m-5" onClick={handleRetrieveAllBooksApi}>
          Retrive All Books</button>
      </div>
      <div className="text-info">
        
        <div>
        <div className="row">
  {/* <div class="col-sm-6 mb-3 mb-sm-0"> */}
          {books.map(
                (book) => {

                  return (
                    <div className="col-sm-6 mb-3 ml-3">
                    <BookCard book = {book}/>
                    </div>
                  );
                }
              )}
              {/* </div> */}
              </div>
        </div>
      </div>
    </div>
  )
}

export default WelcomeComponent