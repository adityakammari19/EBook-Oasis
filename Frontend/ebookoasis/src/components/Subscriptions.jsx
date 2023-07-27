import React, { useEffect, useState } from 'react';
import { useAuth } from '../security/AuthContext';
import { retrieveAllSubscribedBooksApi } from '../api/EBookApiService';
import BookCard from './BookCard';

function Subscriptions() {
  const { username } = useAuth();
  const [subscriptions, setSubscriptions] = useState([]);
  const [loading, setLoading] = useState(true);
  const fromSubscriptionRoute = true;

  useEffect(() => {
    retrieveAllSubscriptions();
  }, []);

  function retrieveAllSubscriptions() {
    setLoading(true);

    retrieveAllSubscribedBooksApi(username)
      .then((response) => {
        setSubscriptions(response.data);
      })
      .catch((error) => console.log(error))
      .finally(() => {
        setLoading(false);
      });
  }

  return (
    <>
      <div> All Subscriptions of the user, {username}</div>
      {loading ? (
        <div className="d-flex justify-content-center bg-light">
          <div className="spinner-border" role="status">
            <span className="sr-only">Loading...</span>
          </div>
        </div>
      ) : (
        <div className="row p-5" style={{ backgroundColor: 'rgb(248, 246, 244)', color: 'black' }}>
          {subscriptions.map((subscription) => {
            return (
              <div className="col-md-6 col-lg-4 mb-3 ml-3 shadow p-3" style={{ justifyContent: 'space-between' }} key={subscription.book.bookId}>
                <BookCard book={subscription.book} fromSubscriptionRoute={fromSubscriptionRoute} />
                {/* <div>{subscription.subscriptionDate}</div> */}
              </div>
            );
          })}
        </div>
      )}
    </>
  );
}

export default Subscriptions;
