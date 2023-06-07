import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import { retrieveAllBooksApi } from '../api/EBookApiService'

function WelcomeComponent() {
  const {username } = useParams()



    const [books, setBooks] = useState([])

    

    function handleRetrieveAllBooksApi(){
        console.log('called handleRetrieveAllBooksApi')
              
        retrieveAllBooksApi()
            .then( (response) => successfulResponse(response) )
            .catch ( (error) => errorResponse(error) )
            .finally ( () => console.log('cleanup') )

    }

    function successfulResponse(response) {
        console.log(response.data)
        //setMessage(response.data)
        response.data.map((book) => {setBooks((data)=>[
          ...data,{
            bookId:book.bookId,
            title:book.title,
            description:book.description,
            author:book.author,
            isbn:book.isbn,
            pageCount:book.pageCount,
            sourceLocation:book.sourceLocation,
            coverImageUrl:book.coverImageUrl

          }
        ])}
        )
        setBooks(response.data)
        console.log(books)
    }

    function errorResponse(error) {
        console.log(error)
    }
  return (
    <div className="WelcomeComponent">
            <h1>Welcome {username}</h1>
            
            <div>
                <button className="btn btn-success m-5" onClick={handleRetrieveAllBooksApi}>
                    Retrive All Books</button>
            </div>
            <div className="text-info">{books}
            <table className="table">
              <thead>
              <tr>
                <th>BookId</th>
                <th>title</th>
                <th>description</th>
                <th>author</th>
                <th>isbn</th>
                <th>pageCount</th>
                <th>sourceLocation</th>
                <th>coverImageUrl</th>
              </tr>
              </thead>
              <tbody>
                    {
                        books.map(
                            book => (
                                <tr key={book.bookId}>
                                    <td>{book.bookId}</td>
                                    <td>{book.title}</td>
                                    <td>{book.description}</td>
                                    <td>{book.author}</td>
                                    <td>{book.isbn}</td>
                                    <td>{book.pageCount}</td>
                                    <td>{book.sourceLocation}</td>
                                    <td>{book.coverImageUrl}</td>
                                   
                                  </tr>
                            )
                        )
                    }
                    </tbody>
            </table>
            
            </div>
        </div>
  )
}

export default WelcomeComponent