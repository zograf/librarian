import axios from "axios";
import "./BookCardCompact.css"
import { API } from "../enviroment";

export default function BookCardCompact({book}) {

    const token = localStorage.getItem("token")

    const addToLib = () => {
        axios.put(API + "/user/preferences/library/" + book.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data);
            })
            .catch(e => alert(e))
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
            }}>
            <p className="book-category shadow">{book.category.keyword}</p>
            <button className="solid-icon-button shadow save-button" onClick={addToLib}>
                <span className="material-symbols-outlined icon">new_label</span>
            </button>
            <div className="flex space-between column standard-padding-xxs book-content-wrapper">
                <div>
                    <p className="book-title">{book.title}</p>
                    <div className="data-grid v-spacer-m">
                        <p className="key-item">By</p>
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