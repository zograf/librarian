import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"

export default function AdditionalSubjects({subjects}) {
    let path = '/user/preferences/additional/'
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const handlePhrase = (e) => setPhrase(e.target.value)

    const [found, setFound] = useState([])
    const [additionalKeywords, setAdditionalKeywords] = useState([])
    useEffect(() => { setAdditionalKeywords(subjects) }, [subjects])
    const [searchSent, setSearchSent] = useState(false)


    const search = (e) => {
        if (phrase.length >= 3)
            axios.post(API + "/subjects/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    setFound(resp.data)
                    setSearchSent(true)
                })
        else if (phrase.length == 0) clear()
        else {
            setFound([])
            setSearchSent(true)
        }
    }
    const clear = () => {
        setFound([])
        setPhrase("")
        setSearchSent(false)
    }
    const add = (keyword) => {
        axios.put(API + path + keyword.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data);
                setAdditionalKeywords((prevList) => {
                    return [...prevList, keyword]
                })
            })
            .catch(e => alert(e))
    }
    const remove = (keyword) => {
        axios.delete(API + path + keyword.id, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data);
                setAdditionalKeywords((prevList) => {
                    return prevList.filter((x) => x.id !== keyword.id)
                })
            })
            .catch(e => alert(e))
    }

    return(
        <div>
            {additionalKeywords.length > 0 && <div className="showing">
                <p className="section-title vi-spacer-xl hi-spacer-xs">Additional Keywords</p>
                <div className="flex center wrap gap-xs">{
                    additionalKeywords.map((keyword) => {return (
                        <button className="flex center showing" style={{paddingRight:'0px'}} disabled={true}>
                            <p>{keyword.keyword}</p>
                            <button className="icon-button"><span className="material-symbols-outlined icon" onClick={() => { remove(keyword) }}>cancel</span></button>
                        </button>
                )})
                }</div>
            </div>}

            <p className="card-body neutral v-spacer-xs vi-spacer-l hi-spacer-xs">Add more Keywords</p>
            <div className="flex v-spacer-xs gap-xs">
                <div className="input-wrapper regular-border v-spacer-xs" style={{paddingRight:'4px'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search" value={phrase} onChange={handlePhrase} onKeyUp={search}/>
                    {searchSent && <button className="icon-button"><span className="material-symbols-outlined icon" onClick={clear}>cancel</span></button>}
                </div>
                {searchSent && 
                    <p className="flex center showing card-body" 
                        style={{
                            height:'50px', 
                            transition:'all 0.08s ease-in-out', 
                            backgroundColor: phrase.length < 3 ? 'rgb(var(--error))' : 'rgb(var(--accent))', 
                            color: phrase.length < 3 ? 'rgb(var(--on-error))' : 'rgb(var(--on-primary-dark))',
                            borderRadius:'var(--input-radius)',
                            padding:'0 16px'
                        }}>{phrase.length < 3 ? 'Phrase Too Short' : found.length} Results
                    </p>
                }
            </div>
            
            <div className="flex center wrap gap-xs">{
                found.map((keyword) => {
                        var found = additionalKeywords.find(x => x.id == keyword.id) != undefined
                        return (<button className={`flex center showing ${found ? 'text-button-selected' : 'outline-button'}`} onClick={() => { if(!found) add(keyword) }}>
                            {keyword.keyword} <p className="neutral hi-spacer-xs">{keyword.relevance}</p>
                        </button>)}
                    )
            }</div>
            
            {(!searchSent || (searchSent && phrase.length < 3)) && <div className="dashed-card showing flex column gap-xs center justify-center">
                <p className="card-title neutral">About Keywords</p>
                <p className="card-body">Each book has multiple keywords that describe it's content. Adding keywords to your favorites will fine tune your recommendations.</p>
            </div>}
            
            {searchSent && phrase.length >= 3 && found.length == 0 && <div className="dashed-card showing flex column gap-xs center justify-center">
                <p className="card-title neutral">No Results</p>
                <p className="card-body">Try something different!</p>
            </div>}

        </div>   
    )

}