import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { retrieveAllBooksApi } from '../api/EBookApiService';
import BookCard from './BookCard';

function WelcomeComponent() {
  const { username } = useParams();
  const fromHomeRoute = true;

  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    handleRetrieveAllBooksApi();
  }, []);

  function handleRetrieveAllBooksApi() {
    setLoading(true);

    retrieveAllBooksApi(username)
      .then((response) => {
        setBooks(response.data);
      })
      .catch((error) => console.log(error))
      .finally(() => {
        setLoading(false);
        console.log('cleanup');
      });
  }

  return (
    <div className="WelcomeComponent " style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }} >
      <h1>Welcome {username}</h1>

      {loading ? (
        <div className="d-flex justify-content-center">
          <div className="spinner-border" role="status">
            {/* <span className="sr-only">Loading...</span> */}
          </div>
        </div>
      ) : (
        <div className="text-info">
          <div>
            <div className="row p-5">
              {books.map((book) => {
                return (
                  <div className="col-md-6 col-lg-4 mb-3 ml-3 shadow p-3" style={{ justifyContent: 'space-between' }} key={book.bookId}>
                    <BookCard book={book} fromHomeRoute={fromHomeRoute} />
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default WelcomeComponent;
