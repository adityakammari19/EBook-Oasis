import React, { useState } from 'react';
import { addPublicationApi, publishNewBookApi } from '../api/EBookApiService';
import { useAuth } from '../security/AuthContext';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';

function PublishNewBook() {
  const { username } = useAuth();
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [pageCount, setPageCount] = useState('');
  const [coverImage, setCoverImage] = useState(null);
  const [sourceFile, setSourceFile] = useState(null);
  const [validationErrors, setValidationErrors] = useState({});
  const [showSuccessPopup, setShowSuccessPopup] = useState(false);
  const [showErrorPopup, setShowErrorPopup] = useState(false);

  const navigate = useNavigate()

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleAuthorChange = (event) => {
    setAuthor(event.target.value);
  };

  const handleIsbnChange = (event) => {
    setIsbn(event.target.value);
  };

  const handlePageCountChange = (event) => {
    setPageCount(event.target.value);
  };

  const handleCoverImageChange = (event) => {
    setCoverImage(event.target.files[0]);
  };

  const handleSourceFileChange = (event) => {
    setSourceFile(event.target.files[0]);
  };

  const validateForm = () => {
    const errors = {};

    if (title.trim() === '') {
      errors.title = 'Title cannot be empty.';
    }

    if (description.trim() === '') {
      errors.description = 'Description cannot be empty.';
    } else if (description.trim().length < 20) {
      errors.description = 'Description should be at least 20 characters long.';
    }

    if (author.trim() === '') {
      errors.author = 'Author cannot be empty.';
    }

    if (isbn.trim() === '') {
      errors.isbn = 'ISBN cannot be empty.';
    }

    if (pageCount.trim() === '') {
      errors.pageCount = 'Page count cannot be empty.';
    } else if (isNaN(pageCount.trim())) {
      errors.pageCount = 'Page count should be numeric.';
    } else if (pageCount.trim() <= 0) {
      errors.pageCount = 'Page count should be greater then 0.';
    }

    if (!coverImage) {
      errors.coverImage = 'Cover image cannot be empty.';
    }

    if (!sourceFile) {
      errors.sourceFile = 'Book source location cannot be empty.';
    }

    setValidationErrors(errors);

    return Object.keys(errors).length === 0;
  };

  const handleSuccessPopup = () => {
    setShowSuccessPopup(false);
    navigate(`/publications`)
  }

  const handlePublish = async (event) => {
    event.preventDefault();

    if (!validateForm()) {
      return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('description', description);
    formData.append('author', author);
    formData.append('isbn', isbn);
    formData.append('pageCount', pageCount);
    formData.append('coverImage', coverImage);
    formData.append('sourceFile', sourceFile);

    try {
      const response = await publishNewBookApi(formData);
      if (response.status === 201) {
        console.log('Files uploaded successfully!');
        const res = await addPublicationApi(username, isbn);
        if (res.status === 201) {
          console.log('Published successfully!');
          setShowSuccessPopup(true);
        }
      }
    } catch (error) {
      console.error('Failed to upload files:', error.response.data.errorMessage);
      setShowErrorPopup(true);
    }
  };

  return (
    <div className="row justify-content-center bg-light">
      <form className="mu-50 col-sm-6 mb-3 ml-3">

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="title">Title</label>
          <input type="text" id="title" className={`form-control ${validationErrors.title ? 'is-invalid' : ''}`} name="title" value={title} onChange={handleTitleChange} />
          {validationErrors.title && <div className="invalid-feedback">{validationErrors.title}</div>}
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="description">Description</label>
          <textarea className={`form-control ${validationErrors.description ? 'is-invalid' : ''}`} id="description" name="description" value={description} onChange={handleDescriptionChange} rows="3"></textarea>
          {validationErrors.description && <div className="invalid-feedback">{validationErrors.description}</div>}
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="author">Author</label>
          <input type="text" id="author" className={`form-control ${validationErrors.author ? 'is-invalid' : ''}`} name="author" value={author} onChange={handleAuthorChange} />
          {validationErrors.author && <div className="invalid-feedback">{validationErrors.author}</div>}
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="isbn">ISBN</label>
          <input type="text" id="isbn" className={`form-control ${validationErrors.isbn ? 'is-invalid' : ''}`} name="isbn" value={isbn} onChange={handleIsbnChange} />
          {validationErrors.isbn && <div className="invalid-feedback">{validationErrors.isbn}</div>}
        </div>

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="pageCount">Page Count</label>
          <input type="text" id="pageCount" className={`form-control ${validationErrors.pageCount ? 'is-invalid' : ''}`} name="pageCount" value={pageCount} onChange={handlePageCountChange} />
          {validationErrors.pageCount && <div className="invalid-feedback">{validationErrors.pageCount}</div>}
        </div>

        <label className="form-label" htmlFor="coverImage">Cover Image</label>
        <div className="input-group mb-4">
          <input type="file" className={`form-control ${validationErrors.coverImage ? 'is-invalid' : ''}`} id="coverImage" onChange={handleCoverImageChange} aria-describedby="coverImage" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="coverImage">Submit Cover Image</button>
          {validationErrors.coverImage && <div className="invalid-feedback">{validationErrors.coverImage}</div>}
        </div>

        <label className="form-label" htmlFor="sourceLocation">Book</label>
        <div className="input-group mb-4">
          <input type="file" className={`form-control ${validationErrors.sourceFile ? 'is-invalid' : ''}`} id="sourceLocation" onChange={handleSourceFileChange} aria-describedby="sourceLocation" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="sourceLocation">Submit File</button>
          {validationErrors.sourceFile && <div className="invalid-feedback">{validationErrors.sourceFile}</div>}
        </div>

        <button type="button" className="btn btn-primary btn-block mb-4" name="publish" onClick={handlePublish}>Publish</button>

        {/* Success Popup */}
        <Modal show={showSuccessPopup} onHide={handleSuccessPopup}>
          <Modal.Header closeButton>
            <Modal.Title>Success</Modal.Title>
          </Modal.Header>
          <Modal.Body>Published new book {title} successfully.</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleSuccessPopup}>Close</Button>
          </Modal.Footer>
        </Modal>

        {/* Error Popup */}
        <Modal show={showErrorPopup} onHide={() => setShowErrorPopup(false)}>
          <Modal.Header closeButton>
            <Modal.Title>Error</Modal.Title>
          </Modal.Header>
          <Modal.Body>Book already exists.</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowErrorPopup(false)}>Close</Button>
          </Modal.Footer>
        </Modal>
      </form>
    </div>
  );
}

export default PublishNewBook;
