import React from 'react'
import { Link } from 'react-router-dom';
import { useAuth } from '../security/AuthContext';

function BookCard({ book, fromSubscriptionRoute ,fromHomeRoute,fromPublicationRoute}) {
    const { author, coverImage, description, isbn, pageCount, sourceFile, title, bookId } = book;

    const {username} =useAuth()


    return (
        <>

            <div className="card" style={{ width: 40+'em' }}>
                <div className="row">
                    <div className="col-md-4">
                        <img src={`data:image/jpeg;base64,${coverImage}`} className="card-img-top img-fluid" alt={title} />
                    </div>
                    <div className="col-md-8">
                        <div className="card-body" style={{ borderWidth: 10 }}>
                            <h5 className="card-title">{title}</h5>
                            <p className="card-text">Author: {author}</p>
                            <p className="card-text">ISBN: {isbn}</p>
                            <p className="card-text">Page Count: {pageCount}</p>
                            <p className="card-text">{description}</p>
                            <Link className="btn btn-primary " to={{pathname:`/users/${username}/books/${bookId}` }}
                          state = {{ fromSubscriptionRoute : fromSubscriptionRoute,fromHomeRoute:fromHomeRoute,fromPublicationRoute:fromPublicationRoute}}>View Book Details</Link> 
                        </div>
                    </div>
                </div>
            </div>
        </>

    );
}

export default BookCard