import React from 'react'

function BookCard({ book }) {
    const { author, coverImageUrl, description, isbn, pageCount, sourceLocation, title } = book;



    return (
        <>

            {/* <div className="container mt-4"> 
        <div className="row"> 
          <div className="col-md-4"> 
            <img src={coverImageUrl} alt={title} className="img-fluid" /> 
          </div> 
          <div className="col-md-8"> 
            <h3>{title}</h3>
            <p>Author: {author}</p> 
            <p>ISBN: {isbn}</p> 
            <p>Page Count: {pageCount}</p> 
            <p>{description}</p> 
            <a href={sourceLocation} className="btn btn-primary" target="_blank" rel="noopener noreferrer"> 
              Read Book 
            </a> 
          </div> 
        </div>  
      </div>  */}

            <div className="card" style={{ width: 40+'em' }}>
                <div className="row">
                    <div className="col-md-4">
                        <img src={coverImageUrl} className="card-img-top img-fluid" alt={title} />
                    </div>
                    <div className="col-md-8">
                        <div className="card-body" style={{ borderWidth: 10 }}>
                            <h5 className="card-title">{title}</h5>
                            <p className="card-text">Author: {author}</p>
                            <p className="card-text">ISBN: {isbn}</p>
                            <p className="card-text">Page Count: {pageCount}</p>
                            <p className="card-text">{description}</p>
                            <a href={sourceLocation} className="btn btn-primary">View Book Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </>

    );
}

export default BookCard