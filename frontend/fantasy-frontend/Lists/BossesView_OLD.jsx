// src/BossesView.jsx
import React, { useEffect, useState } from 'react';
import './fantasyType.css';

export default function BossesView_OLD({ region }) {
  const [bosses, setBosses] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    fetch(`http://localhost:8080/monster?region=${region}&boss=true`)
      .then(r => r.json())
      .then(data => { if (!cancelled) { setBosses(data); setLoading(false); } })
      .catch(() => { if (!cancelled) setLoading(false); });
    return () => { cancelled = true; };
  }, [region]);

  if (loading) return <p>ładowanie bossów…</p>;

  return (
    <div>
      <h2 className="fantasy-header">Bossowie regionu {region.replace(/_/g, ' ')}</h2>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {bosses.map(m => (
          <li key={m.id} className="fantasy-card">
            <h3>
              {m.name}
              <span style={{ fontSize: '0.8em' }}> ({m.type})</span>
            </h3>
            <div><span className="fantasy-label">Opis:</span> {m.description}</div>
            <div><span className="fantasy-label">Słabość:</span> {m.weakness}</div>
            <div><span className="fantasy-label">Siły:</span> {m.strengths}</div>
            <div><span className="fantasy-label">Poziom:</span> {m.level}</div>
          </li>
        ))}
      </ul>
    </div>
  );
}