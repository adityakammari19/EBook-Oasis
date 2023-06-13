import React, { useEffect, useState } from 'react'
import { retriveBookApi, subscribeToABookApi, unSubscribeToABookApi } from '../api/EBookApiService'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../security/AuthContext'
import BookViewer from './BookViewer'

function Book() {
  const { bookId } = useParams()
  const location = useLocation()
  const navigate = useNavigate()
  const { username } = useAuth()

  const { fromSubscriptionRoute, fromHomeRoute, fromPublicationRoute } = location.state
  const [book, setBook] = useState({})
  const [enableRead, setEnableRead] = useState(false)

  useEffect(() => {
    retriveBookWithId(bookId)
  }, [])

  function retriveBookWithId(bookId) {
    retriveBookApi(bookId)
      .then((response) => setBook(response.data))
      .catch((error) => console.log(error))
  }
  async function handleSubscribe() {
    try {
      const response = await subscribeToABookApi(username, bookId)

      if (response.status === 201) {
        console.log("subscribed to book with id :" + bookId)
        navigate(`/subscriptions`)
      }
    }
    catch (error) {
      console.error('Failed to subscribe to the book:', error);
    }
  }
  async function handleUnSubscribe() {
    try {
      const response = await unSubscribeToABookApi(username, bookId)
      if (response.status === 200) {
        navigate(`/welcome/${username}`)
      }
    }
    catch (error) {
      console.error('Failed to Unsubscribe to the book:', error);
    }
  }

  function handleRead() {
    setEnableRead(!enableRead)

  }
  return (<>
    <div>{book.title} </div>
    <div>{fromSubscriptionRoute && <div><button className="btn btn-primary" type="button" onClick={handleRead}>Read</button><button className="btn btn-primary" type="button" onClick={handleUnSubscribe}>UnSubscribe</button></div>}</div>
    <div>{fromHomeRoute && <div><button className="btn btn-primary" type="button" onClick={handleSubscribe}>Subscribe</button></div>}</div>
    <div>{fromPublicationRoute && <div><button className="btn btn-primary" type="button" onClick={handleRead}>Read</button></div>}</div>
    {enableRead && <div><BookViewer book={book} /></div>}
  </>
  )
}

export default Book