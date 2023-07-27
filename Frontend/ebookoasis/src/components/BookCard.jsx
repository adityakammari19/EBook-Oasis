import React from 'react'
import { Link } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';

function BookCard({ book, fromSubscriptionRoute, fromHomeRoute, fromPublicationRoute }) {
    const { author, coverImage, title, bookId } = book;

    const { username } = useAuth()


    return (
        <>

            <div className="card" style={{ backgroundColor: 'rgb(196, 223, 223)', color: 'black' }} >
                <div className="row" style={{ height: '300px' }}>
                    <div className="col-md-5">

                        <div className="image-container justify-content-center" style={{ height: '100px' }}>
                            <img src={`data:image/jpeg;base64,${coverImage}`} className="card-img-top img-fluid img-responsive" alt={title} />
                        </div>
                    </div>
                    <div className="col-md-7">
                        <div className="card-body" style={{ borderWidth: 10 }}>
                            <h5 className="card-title">{title}</h5>
                            <p className="card-text">Author: {author}</p>

                            <Link className="btn btn-primary align-bottom" to={{ pathname: `/users/${username}/books/${bookId}` }}
                                state={{ fromSubscriptionRoute: fromSubscriptionRoute, fromHomeRoute: fromHomeRoute, fromPublicationRoute: fromPublicationRoute }}>View Book Details</Link>
                        </div>
                    </div>
                </div>
            </div>
        </>

    );
}

export default BookCard