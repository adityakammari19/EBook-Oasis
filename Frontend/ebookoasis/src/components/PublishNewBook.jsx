import React, { useState } from 'react'
import { addPublicationApi, publishNewBookApi } from '../api/EBookApiService';
import { useAuth } from '../security/AuthContext';

function PublishNewBook() {
  const {username} = useAuth();
  const [title, setTitle] = useState(''); 
  const [description, setDescription] = useState(''); 
  const [author, setAuthor] = useState(''); 
  const [isbn, setIsbn] = useState(''); 
  const [pageCount, setPageCount] = useState(''); 
  const [coverImage, setCoverImage] = useState(null); 
  const [sourceFile, setSourceFile] = useState(null); 
 
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

  const handlePublish = async (event) => { 
    event.preventDefault(); 
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
      if(response.status === 201){
          console.log('Files uploaded successfully!');
          const res = await addPublicationApi(username,isbn);
          if(res.status === 201){
            console.log('Published successfully!');
          }
      }
    } catch (error) { 
      console.error('Failed to upload files:', error); 
    } 
  }; 

  return (
    <div className='row justify-content-center'>
      <form className='mu-50 col-sm-6 mb-3 ml-3'>

        {/* {showErrorMessage && <div className="errorMessage"> Please enter correct details.</div>} */}

        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="title">Title</label>
          <input type="text" id="title" className="form-control" name="title" value={title} onChange={handleTitleChange} />
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="description">Description</label>
          <textarea className="form-control" id="description" name="description" value={description} onChange={handleDescriptionChange} rows="3"></textarea>
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="author">Author</label>
          <input type="text" id="author" className="form-control" name="author" value={author} onChange={handleAuthorChange} />
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="isbn">ISBN</label>
          <input type="text" id="isbn" className="form-control" name="isbn" value={isbn} onChange={handleIsbnChange} />
        </div>
        <div className="form-outline mb-4">
          <label className="form-label" htmlFor="pageCount">Page Count</label>
          <input type="text" id="pageCount" className="form-control" name="pageCount" value={pageCount} onChange={handlePageCountChange} />
        </div>

        <label className="form-label" htmlFor="coverImageUrl">Cover Image</label>
        <div className="input-group mb-4">

          <input type="file" className="form-control" id="coverImage" onChange={handleCoverImageChange}  aria-describedby="coverImage" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="coverImage">Submit Cover Image</button>
        </div>
        <label className="form-label" htmlFor="sourceLocation">Book</label>
        <div className="input-group mb-4">

          <input type="file" className="form-control" id="sourceLocation" onChange={handleSourceFileChange}  aria-describedby="sourceLocation" aria-label="Upload" />
          <button className="btn btn-outline-secondary" type="button" id="sourceLocation">Submit File</button>
        </div>

        {/* <!-- Submit button --> */}
        <button type="button" className="btn btn-primary btn-block mb-4" name="publish" onClick={handlePublish}>Publish</button>


      </form>
    </div>
  )
}

export default PublishNewBook