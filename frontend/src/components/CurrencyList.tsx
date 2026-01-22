import type { Currencies } from "../types.tsx";
import { useState } from "react";

interface Props {
  currencies: Currencies[];
  darkMode?: boolean;
}

export default function CurrencyList({ currencies, darkMode }: Props) {
  const [filter, setFilter] = useState<string>("");

  return (
    <div className="flex justify-center">
      <div
        className={`w-full sm:w-[90%] md:w-[60%] lg:w-[35%] mt-8 pt-4 pb-8 px-8 rounded-3xl shadow-lg
                    ${darkMode ? "bg-[#2A2544]" : "bg-[#F5F3FF]"}`}
      >
        <label
          className={`py-2 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
        >
          Search
        </label>
        <input
          type="text"
          className={`mb-6 rounded-md w-full pl-2 py-2 outline-none
                            ${
                              darkMode
                                ? "bg-[#1E1B2E] text-white focus:ring-[#7C3AED]"
                                : "bg-white focus:ring-[#C4B5FD]"
                            }`}
          placeholder="Currency name or ISO code..."
          onChange={(e) => {
            if (e.target.value.length < 3) {
              setFilter("");
            }
            if (e.target.value.length >= 3) {
              setFilter(e.target.value);
            }
          }}
        />
        <ul>
          {currencies.map((c) => {
            if (
              c.isoCode.toLowerCase().includes(filter.toLowerCase()) ||
              c.name.toLowerCase().includes(filter.toLowerCase())
            ) {
              return (
                <li
                  key={c.isoCode}
                  className={`flex justify-between border-b-2 py-2
                ${darkMode ? "border-[#7C3AED] text-[#DDD6FE]" : "border-[#C4B5FD]"}`}
                >
                  <p>{c.isoCode}</p>
                  <p>{c.name}</p>
                </li>
              );
            }
          })}
        </ul>
      </div>
    </div>
  );
}
