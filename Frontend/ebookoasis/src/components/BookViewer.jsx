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

      <div className="d-flex justify-content-center bg-black bg-opacity-25" >
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