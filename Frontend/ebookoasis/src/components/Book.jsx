import React, { useEffect, useState } from 'react';
import { retriveBookApi, subscribeToABookApi, unSubscribeToABookApi } from '../api/EBookApiService';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';
import BookViewer from './BookViewer';

function Book() {
  const { bookId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const { username } = useAuth();

  const { fromSubscriptionRoute, fromHomeRoute, fromPublicationRoute } = location.state;
  const [book, setBook] = useState({});
  const [enableRead, setEnableRead] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    retriveBookWithId(bookId);
  }, []);

  function retriveBookWithId(bookId) {
    setLoading(true);

    retriveBookApi(bookId)
      .then((response) => {
        setBook(response.data);
      })
      .catch((error) => console.log(error))
      .finally(() => {
        setLoading(false);
      });
  }

  async function handleSubscribe() {
    try {
      const response = await subscribeToABookApi(username, bookId);

      if (response.status === 201) {
        console.log('subscribed to book with id :' + bookId);
        navigate(`/subscriptions`);
      }
    } catch (error) {
      console.error('Failed to subscribe to the book:', error);
    }
  }

  async function handleUnSubscribe() {
    try {
      const response = await unSubscribeToABookApi(username, bookId);
      if (response.status === 200) {
        navigate(`/welcome/${username}`);
      }
    } catch (error) {
      console.error('Failed to Unsubscribe to the book:', error);
    }
  }

  function handleRead() {
    setEnableRead(!enableRead);
  }

  return (
    <>
      {loading ? (
        <div className="d-flex justify-content-center" style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }}>
          <div className="spinner-border" role="status">
            <span className="sr-only">Loading...</span>
          </div>
        </div>
      ) : (
        <>
          <div className="card m-5" >
            <div className="row">
              <div className="col-md-4">
                <img src={`data:image/jpeg;base64,${book.coverImage}`} className="card-img-top img-thumbnail" style={{ width: '300px' }} alt={book.title} />
              </div>
              <div className="col-md-8 p-5">
                <div className="card-body" style={{ borderWidth: 10 }}>
                  <h2 className="card-title m-2">{book.title}</h2>
                  <div className="card-text m-1">{book.description}</div>
                  <div className="card-text m-1">Written By {book.author}</div>
                  <div className="card-text m-1">{book.pageCount} Pages</div>
                  <div className="card-text m-1">ISBN: {book.isbn}</div>


                  <div>
                    {fromSubscriptionRoute && (
                      <div>
                        <button className="btn btn-primary m-5" type="button" onClick={handleRead}>
                          Read
                        </button>
                        <button className="btn btn-danger ml-5" type="button" onClick={handleUnSubscribe}>
                          UnSubscribe
                        </button>
                      </div>
                    )}
                  </div>
                  <div>
                    {fromHomeRoute && (
                      <div>
                        <button className="btn btn-primary" type="button" onClick={handleSubscribe}>
                          Subscribe
                        </button>
                      </div>
                    )}
                  </div>
                  <div>
                    {fromPublicationRoute && (
                      <div>
                        <button className="btn btn-primary" type="button" onClick={handleRead}>
                          Read
                        </button>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>



          {enableRead && <div><BookViewer book={book} /></div>}
        </>
      )}
    </>
  );
}

export default Book;
