import axios from "axios";
import "./BookCardCompact.css"
import { API } from "../enviroment";
import { useEffect, useState } from "react";

export default function BookCardCompact({book, isLiked, inLibrary = false, onClick, isRead}) {

    const token = localStorage.getItem("token")
    const [liked, setLiked] = useState(isLiked)
    useEffect(() => setLiked(isLiked), [isLiked])

    const addToLib = (e) => {
        console.log(e);
        e.stopPropagation()
        if(liked) {
            axios.delete(API + "/user/preferences/library/" + book.id, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(_ => setLiked(false) )
            .catch(e => alert(e))
        }
        else {
            axios.put(API + "/user/preferences/library/" + book.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
                .then(_ => setLiked(true))
                .catch(e => alert(e))
        }
    }
    return(
        <div
            className="card book-card" 
            style={{
                width:'100%',
                aspectRatio:'9/14', 
                padding: '0',
                overflow: "hidden",
                backgroundImage: "url(" + book.cover + "), url(https://static-01.daraz.pk/p/9c4bbb21ac32475a2f3d8c55d2b7337d.jpg_750x750.jpg_.webp)",
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
                position: "relative"
            }}
            onClick={onClick}
        >
            <p className="book-category shadow">{book.category.keyword}</p>
            {!isRead && <button className="solid-icon-button shadow save-button" onClick={addToLib}>
                <span className="material-symbols-outlined icon">{liked ? 'label_off' : 'new_label'}</span>
            </button>}
            <div className= {`flex space-between column standard-padding-xxs book-content-wrapper ${inLibrary && !liked? 'taller-book-content' : ''}`}>
                {inLibrary && !liked && 
                    <div className="warrning-chip flex center gap-xs">
                        <span className="material-symbols-outlined icon">warning</span>
                        <p>Book removal pending</p>
                    </div>
                }
                <div className="book-content-inner">
                    <p className="book-title">{book.title}</p>
                    <div className="data-grid v-spacer-m">
                        <p className="key-item" style={{paddingRight:'0.25em'}}>By</p>
                        <p className="value-item">{book.authors[0].name}</p>
                    </div>
                    <div className="flex wrap gap-xxs">
                        {book.subjects.map(subject => {return(<p className="subject-chip">{subject.keyword}</p>)})}
                    </div>
                </div>
            </div>
        </div>
    )
}