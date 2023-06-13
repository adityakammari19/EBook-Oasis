import React, { useState } from 'react'
import { Document, Page, pdfjs } from 'react-pdf';

import 'react-pdf/dist/esm/Page/AnnotationLayer.css';
import 'react-pdf/dist/esm/Page/TextLayer.css';
function BookViewer(props) {
  // pdfjs.GlobalWorkerOptions.workerSrc = `pdfjs-dist/build/pdf.worker.js`;
  pdfjs.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.6.172/pdf.worker.js';

  const [numPages, setNumPages] = useState(null);

  const onDocumentLoadSuccess = ({ numPages }) => {
    setNumPages(numPages);
  };
  return (<>
    <div>
      <h2>{props.book.title}</h2>
      <p>Author: {props.book.author}</p>
      <p>ISBN: {props.book.isbn}</p>
      <p>Page Count: {props.book.pageCount}</p>
      {/* <p>{props.book.sourceFile}</p> */}
      <div>
        <img src={`data:image/jpeg;base64,${props.book.coverImage}`} alt={props.book.title} />
      </div>
      <div>
        <Document
          file={`data:application/pdf;base64,${props.book.sourceFile}`}
          onLoadSuccess={onDocumentLoadSuccess}
        >
          {Array.from(new Array(numPages), (el, index) => (
            <Page key={index} pageNumber={index + 1} />
          ))}
        </Document>
      </div>
    </div>
  </>
  )
}

export default BookViewer