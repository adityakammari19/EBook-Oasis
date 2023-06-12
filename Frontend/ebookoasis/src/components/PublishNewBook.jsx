import React, { useState } from 'react'

function PublishNewBook() {
  const [book, setBook] = useState({
    title: "",
    author: "",
    isbn: "",
    description: "",
    coverImageUrl: "",
    sourceLocation: ""

  })

  const [showErrorMessage, setShowErrorMessage] = useState(false)

  const handleChange = e => {
    const { name, value } = e.target;
    setBook(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  async function handlePublish() {

  }

  return (
    <div className='row justify-content-center'>
      <form className='mu-50 col-sm-6 mb-3 ml-3'>

        {showErrorMessage && <div className="errorMessage"> Please enter correct details.</div>}

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="title">Title</label>
          <input type="text" id="title" className="form-control" name="title" value={book.title} onChange={handleChange} />
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="description">Description</label>
          <textarea class="form-control" id="description" name="description" value={book.description} onChange={handleChange} rows="3"></textarea>
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="author">Author</label>
          <input type="text" id="author" className="form-control" name="author" value={book.author} onChange={handleChange} />
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="isbn">ISBN</label>
          <input type="text" id="isbn" className="form-control" name="isbn" value={book.isbn} onChange={handleChange} />
        </div>

        <label className="form-label" htmlFor="coverImageUrl">Cover Image</label>
        <div className="input-group mb-4">

          <input type="file" className="form-control" id="coverImageUrl" aria-describedby="coverImageUrl" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="coverImageUrl">Submit Cover Image</button>
        </div>
        <label className="form-label" htmlFor="sourceLocation">Book</label>
        <div className="input-group mb-4">

          <input type="file" className="form-control" id="sourceLocation" aria-describedby="sourceLocation" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="sourceLocation">Submit File</button>
        </div>

        {/* <!-- Submit button --> */}
        <button type="button" className="btn btn-primary btn-block mb-4" name="publish" onClick={handlePublish}>Publish</button>


      </form>
    </div>
  )
}

export default PublishNewBook