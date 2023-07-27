import React, { useEffect, useState } from 'react';
import { useAuth } from '../security/AuthContext';
import { retrieveAllPublishedBooksApi } from '../api/EBookApiService';
import BookCard from './BookCard';
import { Link } from 'react-router-dom';

function Publications() {
  const { username } = useAuth();
  const [publications, setPublications] = useState([]);
  const [loading, setLoading] = useState(true);
  const fromPublicationRoute = true;

  useEffect(() => {
    retrieveAllPublications();
  }, []);

  function retrieveAllPublications() {
    setLoading(true);

    retrieveAllPublishedBooksApi(username)
      .then((response) => {
        setPublications(response.data);
      })
      .catch((error) => console.log(error))
      .finally(() => {
        setLoading(false);
      });
  }

  return (
    <>
      <div style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }}>
        <div>
          <Link className="btn btn-primary mb-5" to="/publish">Publish New Book</Link>
        </div>
        <div> All Publications of the user, {username}</div>
        {loading ? (
          <div className="d-flex justify-content-center " style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }}>
            <div className="spinner-border" role="status">
              <span className="sr-only">Loading...</span>
            </div>
          </div>
        ) : (
          <div className="row p-5" style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }}>
            {publications.map((publication) => {
              return (
                <div className="col-md-6 col-lg-4 mb-3 ml-3 shadow p-3" style={{ justifyContent: 'space-between' }} key={publication.book.bookId}>
                  <BookCard book={publication.book} fromPublicationRoute={fromPublicationRoute} publishedDate={publication.publicationDate} />
                </div>
              );
            })}
          </div>
        )}
      </div>
    </>
  );
}

export default Publications;
