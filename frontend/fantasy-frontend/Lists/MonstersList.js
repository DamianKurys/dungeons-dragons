import React, { useEffect, useState } from 'react';
import './fantasyType.css';


function MonstersList({ region }) {
  const [monsters, setMonsters] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetch(`http://localhost:8080/monster/region/${region}`)
      .then(res => res.json())
      .then(data => {
        setMonsters(data);
        setLoading(false);
      });
  }, [region]);

  if (loading) return <p>Ładowanie potworów...</p>;

  return (
    <div>
    <h2 className="fantasy-header">
      Potwory regionu {region.replace(/_/g, ' ')} </h2>
    <ul style={{listStyle: "none", padding: 0}}>
      {monsters.map(monster => (
        <li key={monster.id} className="fantasy-card">
          <h3>
            {monster.name}
            <span style={{fontSize: "0.8em", color: "#b88c4c"}}> ({monster.type})</span>
          </h3>
          <div><span className="fantasy-label">Opis:</span> {monster.description}</div>
          <div><span className="fantasy-label">Słabość:</span> {monster.weakness}</div>
          <div><span className="fantasy-label">Siły:</span> {monster.strengths}</div>
          <div><span className="fantasy-label">Poziom:</span> {monster.level}</div>
        </li>
      ))}
    </ul>
  </div>
);
}

export default MonstersList;